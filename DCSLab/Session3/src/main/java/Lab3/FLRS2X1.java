package Lab3;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;
import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFuzzy;
import DataOnly.FLRS;
import DataOnly.FV;
import DataOnly.Fuzzy;
import DataOnly.FuzzyVector;
import DataOnly.PlaceNameWithWeight;
import Enumerations.FZ;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class FLRS2X1 {
    public static void main(String[] args) {

        // Step 1: Define the FLRS table as shown in your example
        FLRS flrs2x1 = new FLRS(
                new FV(FZ.PL), new FV(FZ.PM), new FV(FZ.ZR), new FV(FZ.NL), new FV(FZ.ZR),
                new FV(FZ.PL), new FV(FZ.NM), new FV(FZ.PL), new FV(FZ.PL), new FV(FZ.NM),
                new FV(FZ.NL), new FV(FZ.PL), new FV(FZ.ZR), new FV(FZ.ZR), new FV(FZ.PL),
                new FV(FZ.ZR), new FV(FZ.ZR), new FV(FZ.NM), new FV(FZ.PM), new FV(FZ.NL),
                new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.ZR), new FV(FZ.NM), new FV(FZ.PL));

        flrs2x1.Print(); // Print the FLRS table to verify setup

        // Step 2: Initialize Petri net and places
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Main Petri";
        pn.NetworkPort = 1081;

        // Define input places with fuzzy values
        DataFuzzy p1 = new DataFuzzy();
        p1.SetName("P1");
        p1.SetValue(new Fuzzy(0.1F)); // x1 = 0.1 as given
        pn.PlaceList.add(p1);

        DataFuzzy p2 = new DataFuzzy();
        p2.SetName("P2");
        p2.SetValue(new Fuzzy(0.2F)); // x2 = 0.2 as given
        pn.PlaceList.add(p2);

        // Define output place
        DataFuzzy p3 = new DataFuzzy();
        p3.SetName("P3");
        pn.PlaceList.add(p3);

        // Step 3: Define transition with FLRS logic
        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "T1";
        t1.InputPlaceName.add("P1");
        t1.InputPlaceName.add("P2");

        // Set up transition conditions
        Condition T1Ct1 = new Condition(t1, "P1", TransitionCondition.NotNull);
        Condition T1Ct2 = new Condition(t1, "P2", TransitionCondition.NotNull);
        T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition = T1Ct1;

        // Define input weights and connections to apply FLRS logic
        ArrayList<PlaceNameWithWeight> input = new ArrayList<>();
        input.add(new PlaceNameWithWeight("P1", 1F));   // Weight for P1 (x1) as given: w1 = 1
        input.add(new PlaceNameWithWeight("P2", -1F));  // Weight for P2 (x2) as given: w2 = -1

        // Define output place
        ArrayList<String> output = new ArrayList<>();
        output.add("P3");

        // Activation to apply FLRS logic and produce output
        grdT1.Activations.add(new Activation(t1, flrs2x1, input, TransitionOperation.FLRS, output));
        t1.GuardMappingList.add(grdT1);

        t1.Delay = 0;
        pn.Transitions.add(t1);

        // Start the Petri net simulation
        System.out.println("Experiment started\n------------------------------");
        pn.Delay = 3000;
        // pn.Start(); // Uncomment this line to start the Petri net (if applicable)

        // Display the Petri net window
        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}