#!/usr/bin/env python3
'''
Script to check PROJECT_EXTERNAL_SOURCE_PATH property in .pydevproject is up-to-date with
the target platform in daq-aggregator.git/target-platform/dawn/uk.ac.diamond.dawn.tp.target
'''

import logging
from pathlib import Path
import xml.etree.ElementTree as ET

TP_FILE='daq-aggregator.git/target-platform/dawn/uk.ac.diamond.dawn.tp.target'
PROP_NAME='org.python.pydev.PROJECT_EXTERNAL_SOURCE_PATH'
PYDEVPROJECT='.pydevproject'

def check_and_update(project_file, target_file=None, output_file_or_dir=None):
    project_path = Path(project_file).resolve()
    if not project_path.is_file():
        raise ValueError(f'{project_path} is not a file')

    if target_file is None:
        workspace_path = project_path.parent.parent.parent
        target_file = workspace_path / TP_FILE
        logging.info(f'Found target file: {target_file}')
    else:
        target_file = Path(target_file).resolve()

    if not target_file.is_file():
        raise ValueError(f'{project_path} is not a file')

    project_tree, plugins = get_plugins(project_file)
    if get_and_update_versions(plugins, target_file):
        if output_file_or_dir is None:
            import sys
            if sys.hexversion >= 0x03090000:
                ET.indent(project_tree, space='  ') # this is only available on 3.9+
            out = sys.stdout
        else:
            output_file_or_dir = Path(output_file_or_dir)
            if output_file_or_dir.is_dir():
                output_file = output_file_or_dir / PYDEVPROJECT
            else:
                output_file = output_file_or_dir
            if output_file.is_dir():
                raise ValueError(f'Output file |{output_file}| should not be a directory')
            elif output_file.exists():
                logging.warning(f'Overwriting output file {output_file}')
            out = str(output_file)
        project_tree.write(out)
    else:
        print('Nothing updated')

def get_plugins(project_file):
    xml = ET.parse(str(project_file))
    plugins = {}
    for t in xml.iter('pydev_pathproperty'):
        if t.attrib['name'] == PROP_NAME:
            for px in t.iter('path'):
                logging.debug(f'Found path {px}')
                pt = px.text
                i = pt.find('plugins/')
                if i > 0:
                    i += 8
                    j = pt.find('_', i)
                    k = pt.find('.jar', j)
                    logging.debug(f'Adding plugin {pt[i:j]} with {pt[j+1:k]}')
                    plugins[pt[i:j]] = (px, pt[j+1:k]) # set name,version
    return xml, plugins

def get_and_update_versions(plugins, target_file):
    txml = ET.parse(str(target_file))
    tp = txml.find('target')
    locs = txml.find('locations')
    updated = False
    for loc in locs.iter('location'):
        for ux in txml.iter('unit'):
            n = ux.attrib['id']
            v = ux.attrib['version']
            logging.debug(f'Unit has {n} with {v}')
            if n in plugins:
                logging.debug(f'Checking tp plugin {n} with {v}')
                px, ov = plugins[n]
                if ov != v:
                    pt = px.text
                    px.text = pt.replace(ov,v)
                    updated = True
                    logging.info(f'Replaced {ov} with {v}: {px.text}')
    return updated

def create_parser():
    import argparse
    parser = argparse.ArgumentParser(description='PyDevProject updater')
    parser.add_argument('-o', '--output', help='str: output file or directory')
    parser.add_argument('-D', '--debug', help='show debugging information', action='store_true')
    parser.add_argument('input', help='input pydev project file (default: %(default)s', default=PYDEVPROJECT, nargs='?')
    parser.add_argument('target', help='input target platform file (defaults to finding Dawn target from location of input file)', nargs='?')
    return parser

if __name__ == '__main__':
    args = create_parser().parse_args()
    logging.getLogger().setLevel(logging.DEBUG if args.debug else logging.INFO)
    check_and_update(args.input, args.target, args.output)
