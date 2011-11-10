/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis.utils.optimisation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The genetic algorithm implementation, This uses differential evolution for the
 * mutation usage
 * 
 * ga = geneticAlgorithm()
 * ga.optimise(problem, numberOfEpochs, populationPerEpoch, archivePath)
 * 
 * the archive path is for restarting, and setting up an epoch from already created data
 */
public class GeneticAlgorithm {

	private static final Logger logger = LoggerFactory.getLogger(GeneticAlgorithm.class);

	// set some factors
	static final double MUTANT_PROPORTION = 0.5;
	static final double MUTANT_SCALING = 0.5;
	static final int PARAMETER_SCALING = 10;
	static final int COMPETING_PARENTS = 2;
	static final int CROSSOVER_POINT = -1;
	static final int NUMBER_FOR_COMPETITION = 2;

	// generate a new random number generator
	Random rand = new Random();

	private Member newEpoch[];
	private Member oldEpoch[];

	private int totalEpochs;

	private int epochPosition; 

	private int epochSize;

	ProblemDefinition objectiveFunction;

	private BufferedReader br;

	private BufferedWriter bw;

	private int epochNumber;


	/**
	 * 
	 * @param problem
	 * @param numberOfEpochs
	 * @param populationPerEpoch
	 * @param logName if null, then no logging
	 * @param startParameters
	 * @throws Exception
	 */
	public void optimise(ProblemDefinition problem, int numberOfEpochs, int populationPerEpoch, String logName, double[] startParameters) throws Exception {

		logger.debug("entering optimize");
		
		epochSize = populationPerEpoch;

		totalEpochs = numberOfEpochs;

		objectiveFunction = problem;

		// first generate the first epoch
		newEpoch = new Member[epochSize];

		if (logName != null) {
			// then Open the file and create the first log entries
			bw = new BufferedWriter(new FileWriter(logName));
		} else
			bw = null;

		// note the size of the epochs
		if (bw != null) {
			bw.write("epoch Size = "+epochSize+"\n");
			bw.flush();
		}

		// Add the first member, it should be with the start values
		newEpoch[0] = new Member(startParameters, objectiveFunction);

		epochNumber = 0;

		epochPosition = 1;

		try {

			if (bw != null) {
				writeMember(epochNumber, newEpoch[0]);
			}

			logger.debug("running epochs");
			runEpochs();

		} finally {
			if (bw != null)
				bw.close();
		}

	}

	/**
	 * 
	 * @param problem
	 * @param numberOfEpochs
	 * @param logName
	 * @throws Exception
	 */
	public void optimise(ProblemDefinition problem, int numberOfEpochs, String logName) throws Exception {

		logger.debug("entering optimize");

		objectiveFunction = problem;

		// first try to load the file in 
		br = new BufferedReader(new FileReader(logName));

		// try to read the first line to set up some of the necessary parameters
		epochSize = Integer.parseInt(br.readLine().split("=")[1].trim());

		totalEpochs = numberOfEpochs;

		// first generate the first epoch
		newEpoch = new Member[epochSize];

		epochNumber = 0;

		epochPosition = 0;

		logger.debug("loading epochs");
		
		// now try to read the epochs
		readEpochs();		

		// now close the file stream, and open it again to 
		// write to it
		br.close();

		bw = new BufferedWriter(new FileWriter(logName,true));

		logger.debug("running epochs");
		
		try {

			// then complete the number of epochs with the normal 
			// run command. however, if we are at the start of
			// a new epoch, we need to get the best value
			if(epochPosition == 0) {
				newEpoch[0] = getBestMember(oldEpoch);

				writeMember(epochNumber, newEpoch[0]);

			}		

			// then carry on with the standard method
			runEpochs();

		} finally {
			bw.close();
		}

	}


	private void readEpochs() {
		while (epochNumber < totalEpochs) {

			while (epochPosition < epochSize) {
				if(!readNextMember()) {
					return;
				}
				// increment the epochPosition
				epochPosition++;
			}

			// switch the epochs
			oldEpoch = newEpoch;

			// create a new epoch
			newEpoch = new Member[oldEpoch.length];

			epochPosition = 0;
			// increment the epoch number
			epochNumber++;
		}
	}

	/**
	 * 
	 * @return true if this has been read
	 */
	private boolean readNextMember() {

		String memberString;

		try {
			// strip off the head element
			memberString = br.readLine().split(":\t")[1];

		} catch (Exception e) {
			return false;
		}

		newEpoch[epochPosition] = new Member(memberString);

		return true;


	}

	private void writeMember(int epochNumber, Member member) throws IOException {
		bw.append(epochNumber +":\t"+member.saveToString()+"\n");
		bw.flush();		
	}

	private void runEpochs() throws Exception {

		while (epochNumber < totalEpochs) {

			while (epochPosition < epochSize) {
				generateNextMember();
				if (bw != null) {
					writeMember(epochNumber, newEpoch[epochPosition-1]);
				}
			}

			// switch the epochs
			oldEpoch = newEpoch;

			// create a new epoch
			newEpoch = new Member[oldEpoch.length];

			// increment the epoch number
			epochNumber++;

			// then seed the next epoch with the best member from the 
			// last epoch.
			newEpoch[0] = getBestMember(oldEpoch);

			if (bw != null) {
				writeMember(epochNumber, newEpoch[0]);
			}

			epochPosition = 1;

		}

	}

	private Member getBestMember(Member[] epoch) throws Exception {
		double min = epoch[0].fitness;
		int minPos = 0;

		for(int i = 1; i < epoch.length; i++) {
			if (epoch[i] != null) {
				if (epoch[i].fitness < min) {
					min = epoch[i].fitness;
					minPos = i;
				}
			}
		}

		return new Member(epoch[minPos].parameters, objectiveFunction);
	}

	@SuppressWarnings("unused")
	private String status(Member[] epoch) {
		double max = epoch[0].fitness;
		double min = epoch[0].fitness;
		double total = epoch[0].fitness;
		int count = 1;

		for(int i = 1; i < epoch.length; i++) {
			if (epoch[i] != null) {
				if(epoch[i].fitness > max) {
					max = epoch[i].fitness;
				} else if (epoch[i].fitness < min) {
					min = epoch[i].fitness;
				}
				total += epoch[i].fitness;
				count++;
			}
		}

		return "Max = "+max+" : Min = "+min+" : Mean = "+total/count;
	}

	private void generateNextMember() throws Exception {

		if(oldEpoch == null) {
			// generate a new random member
			newEpoch[epochPosition] = new Member(objectiveFunction);

		} else {
			// generate a new member form the previous epoch
			int x = Math.abs(rand.nextInt()) % oldEpoch.length;
			int y = Math.abs(rand.nextInt()) % oldEpoch.length;

			newEpoch[epochPosition] = new Member(compete(oldEpoch), compete(oldEpoch), oldEpoch[x], oldEpoch[y], objectiveFunction);

		}

		// increment the epochPosition
		epochPosition++;

	}

	private Member compete(Member[] epoch) {
		// get the first competitor
		int best = Math.abs(rand.nextInt()) % epoch.length;

		for (int i = 0; i < NUMBER_FOR_COMPETITION - 1; i++) {
			int competitor = Math.abs(rand.nextInt()) % epoch.length;
			if (epoch[competitor].fitness < epoch[best].fitness) {
				best = competitor;
			}
		}

		return epoch[best];

	}

	class Member {

		double[] parameters;
		double fitness;

		public double get(int index) {
			return parameters[index];
		}

		public Member(ProblemDefinition problem) throws Exception {
			parameters = new double[problem.getNumberOfParameters()];
			for (int i = 0; i < parameters.length; i++) {
				parameters[i] = rand.nextDouble();
			}
			fitness(problem);
		}

		public Member(double[] params, ProblemDefinition problem) throws Exception {
			parameters = new double[params.length];
			for(int i = 0; i < params.length; i++) {
				parameters[i] = params[i];
			}
			fitness(problem);
		}

		public Member(Member mum, Member dad, Member x, Member y, ProblemDefinition objectiveFunction) throws Exception{

			parameters = new double[objectiveFunction.getNumberOfParameters()];
			breed(mum, dad);
			mutate(x, y);
			fitness(objectiveFunction);
		}

		public Member(String memberString) {
			loadFromString(memberString);
		}

		private void fitness(ProblemDefinition objectiveFunction) throws Exception {
			fitness = objectiveFunction.eval(parameters);			
		}

		private void breed(Member mum, Member dad) {
			float mumness = rand.nextFloat();

			for (int i = 0; i < parameters.length; i++) {
				if (rand.nextFloat() > mumness) {
					parameters[i] = dad.get(i);
				} else {
					parameters[i] = mum.get(i);
				}
			}
		}

		private void mutate(Member x, Member y) {
			if (rand.nextDouble() > MUTANT_PROPORTION) {

				// looks a bit odd, but this is because we are using differential
				// Evolution

				for (int i = 0; i < parameters.length; i++) {
					double newValue = parameters[i] + ((x.get(i) - y.get(i)) * MUTANT_SCALING);

					newValue = Math.abs(newValue);

					newValue = newValue - Math.floor(newValue);

					parameters[i] = newValue;
				}
			}	
		}

		public String saveToString() {
			String value = "";
			for(int i = 0; i < parameters.length; i++) {
				value = value + parameters[i] + "\t";
			}
			// then finally add the fitness
			value = value + fitness;
			return value;
		}

		public void loadFromString(String input) {
			String[] values = input.split("\t");
			parameters = new double[values.length-1];
			for(int i = 0; i < values.length-1; i++) {
				parameters[i] = Double.parseDouble(values[i]);
			}
			fitness = Double.parseDouble(values[values.length-1]);

		}

		@Override
		public String toString() {
			return saveToString();
		}



	}

}
