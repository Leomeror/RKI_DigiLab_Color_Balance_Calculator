import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        // Index sequences are saved in the array indexes
        // new array list indexes is initialized
        List<String> indexes = new ArrayList<String>();

        // initialize lengthOfIndexes
        int lengthOfIndexes = 0;

        System.out.println("Reading from file.");
        // all indexes are read from file, one index per line
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("input/indexes.txt"));
            //reader = new BufferedReader(new FileReader("input/indexes_test2.txt"));
            // each line contains one index
            String line = reader.readLine();

            // indexes are written in the form of: 1:GTCAGTCA
            String[] indexLine = line.split(":");
            // index number
            String indexName = indexLine[0];
            // index sequence, transformed to upper case, in case input file is written in lower case
            String indexSequence = indexLine[1].toUpperCase();

            lengthOfIndexes = indexSequence.length();
            String firstIndexName = indexName;

            System.out.println("First index is: " + indexSequence);

            while (line != null) {
                // Do the same for all of the lines

                // indexes are written in the form of: 1:GTCAGTCA
                indexLine = line.split(":");
                // index number
                indexName = indexLine[0];
                // index sequence, transformed to upper case, in case input file is written in lower case
                indexSequence = indexLine[1].toUpperCase();
                // add index to array indexes
                indexes.add(indexSequence);

                // Check if all indexes have equal length
                if (lengthOfIndexes != indexSequence.length()) {
                    int indexLength = indexSequence.length();
                    System.out.println("Index " + firstIndexName + " of length " + lengthOfIndexes +
                            " and index " + indexName + " of length " + indexLength + " do not have the same length");
                }

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // red lights per cycle
        int[] redsPerCycle = new int[lengthOfIndexes];
        // green lights per cycle
        int[] greensPerCycle = new int[lengthOfIndexes];

        // fill redsPerCycle and greensPerCycle with int 0
        for (int i = 0; i < redsPerCycle.length; i++) {
            redsPerCycle[i] = 0;
            greensPerCycle[i] = 0;
        }

        System.out.println("Indexes have length: " + lengthOfIndexes);

        // Success
        boolean success = true;

        // cycle
        int cycle = 1;
        // Go through array of index sequences until it's empty
        boolean indexesNotEmpty = true;
        // Each loop is a cycle
        while (indexesNotEmpty) {

            // Go through each index
            for (int i = 0; i < indexes.size(); i++) {
                // get current index
                String index = indexes.get(i);
                // get first base of index
                char base = index.charAt(0);
                // if base is 'G' don't add anything (redundant, but for clarity)
                if (base == 'G') {
                    redsPerCycle[cycle-1] += 0;
                    greensPerCycle[cycle-1] += 0;
                }
                // If base is 'T' add to greens of this cycle
                else if (base == 'T') {
                    redsPerCycle[cycle-1] += 0;
                    greensPerCycle[cycle-1] += 1;
                }
                // If base is 'C' add to reds of this cycle
                else if (base == 'C') {
                    redsPerCycle[cycle-1] += 1;
                    greensPerCycle[cycle-1] += 0;
                }
                // If base is 'A' add to reds and greens of this cycle
                else if (base == 'A') {
                    redsPerCycle[cycle-1] += 1;
                    greensPerCycle[cycle-1] += 1;
                }
                // If base is none of them print Error message
                else {
                    System.out.println("Error! index contains letter that is not A,C,G or T: " + base);
                }
                // If this wasn't the last base in the index
                if (index.length() != 1) {
                    // put back the index without the first character into the indexes array
                    indexes.set(i, index.substring(1));
                }
                // otherwise end the while loop
                else {
                    indexesNotEmpty = false;
                }

            }

            // If reds or greens for this cycle is 0, the whole run is a failure
            if ((redsPerCycle[cycle-1] == 0) | (greensPerCycle[cycle-1] == 0)) {
                success = false;
            }

            if (indexesNotEmpty) {
                // Iterate counter to next cycle
                cycle = cycle + 1;
            }
        }

        if (cycle != lengthOfIndexes) {
            System.out.println("Cycles and index lengths are not the same! " +
                    "Cycles: " + cycle + " index lengths: " + lengthOfIndexes);
        }
        // print output: cycles, reds, greens, success/failure
        System.out.println("CYCLE | RED | GREEN");
        for (int i = 0; i < cycle; i++) {
            System.out.println(i+1 + " | " + redsPerCycle[i] + " | " + greensPerCycle[i]);
        }
        System.out.println(" ");
        if (success) {
            System.out.println("Evaluation: SUCCESS");
        }
        else {
            System.out.println("Evaluation: FAILURE");
        }

    }
}