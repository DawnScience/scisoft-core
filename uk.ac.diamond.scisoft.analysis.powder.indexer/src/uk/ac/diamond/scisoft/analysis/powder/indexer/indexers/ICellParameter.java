package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

public interface ICellParameter {
	
	public void setFigureMerit(double m);

	public double getFigureMerit();

	public String getIndexerIdentifer();

	public void setIndexerIdentifer(String indexerIdentifer);
	
}
