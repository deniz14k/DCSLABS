package main.java.Lab4.Part3Ex3;

import DataObjects.DataTransfer;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFuzzy;
import DataOnly.*;
import DataOnly.FLRS;
import DataOnly.FV;
import DataOnly.Fuzzy;
import DataOnly.FuzzyVector;
import DataOnly.PlaceNameWithWeight;
import DataOnly.TransferOperation;
import Enumerations.FZ;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ORC {
    public static void main(String[] args) throws FileNotFoundException  {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "ORC";
        pn.NetworkPort = 1084;

        pn.SetInputFile("C:/Users/deniz/Downloads/DCSLABS/DCSLab/Session4/PetriInputData/OutsideTemp.txt");

        FLRS reader = new FLRS(new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
                new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
                new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
                new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
                new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL));

        FLRS doubleChannelDifferentiator = new FLRS(new FV(FZ.ZR, FZ.ZR), new FV(FZ.NM, FZ.NM), new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL),new FV(FZ.NL, FZ.NL),
                new FV(FZ.PM, FZ.PM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.NM, FZ.NM), new FV(FZ.NL, FZ.NL),new FV(FZ.NL, FZ.NL),
                new FV(FZ.PL, FZ.PL), new FV(FZ.PM, FZ.PM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.NM, FZ.NM),new FV(FZ.NL, FZ.NL),
                new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL), new FV(FZ.PM, FZ.PM), new FV(FZ.ZR, FZ.ZR),new FV(FZ.NM, FZ.NM),
                new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL), new FV(FZ.PM, FZ.PM),new FV(FZ.ZR, FZ.ZR));

        FLRS doubleChannelAdder = new FLRS(new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM),new FV(FZ.ZR, FZ.ZR),
                new FV(FZ.NL, FZ.NL), new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR),new FV(FZ.PM, FZ.PM),
                new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM),new FV(FZ.PL, FZ.PL),
                new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL),new FV(FZ.PL, FZ.PL),
                new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PL),new FV(FZ.PL, FZ.PL));

        FLRS OneXOneDefaultTable = new FLRS(new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM),new FV(FZ.PL));

        FLRS t2FLRS = new FLRS(new FV(FZ.PL), new FV(FZ.PM), new FV(FZ.ZR), new FV(FZ.NM),new FV(FZ.NL));

        reader.Print();
        doubleChannelDifferentiator.Print();
        OneXOneDefaultTable.Print();
        doubleChannelAdder.Print();


        DataFuzzy p1 = new DataFuzzy();
        p1.SetName("P1");
        pn.PlaceList.add(p1);

        DataFuzzy p2 = new DataFuzzy();
        p2.SetName("P2");
        pn.PlaceList.add(p2);

        DataFuzzy p3 = new DataFuzzy();
        p3.SetName("P3");
        pn.PlaceList.add(p3);

        DataFuzzy p4 = new DataFuzzy();
        p4.SetName("P4");
        pn.PlaceList.add(p4);

        DataTransfer p5 = new DataTransfer();
        p5.SetName("P5");
        p5.Value = new TransferOperation("localhost", "1083", "P1");
        pn.PlaceList.add(p5);

        DataFuzzy P0 = new DataFuzzy();
        P0.SetName("P0");
        P0.SetValue(new Fuzzy(0.0F));
        pn.PlaceList.add(P0);

        // T0 ------------------------------------------------
        PetriTransition t0 = new PetriTransition(pn);
        t0.TransitionName = "T0";

        t0.InputPlaceName.add("P1");
        t0.InputPlaceName.add("P0");

        Condition T0Ct1 = new Condition(t0, "P1", TransitionCondition.NotNull);
        Condition T0Ct2 = new Condition(t0, "P0", TransitionCondition.NotNull);
        T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

        GuardMapping grdT0 = new GuardMapping();
        grdT0.condition = T0Ct1;

        ArrayList<PlaceNameWithWeight> input0 = new ArrayList<>();
        input0.add(new PlaceNameWithWeight("P1", 1F));
        input0.add(new PlaceNameWithWeight("P0", 1F));

        ArrayList<String> Output0 = new ArrayList<>();
        Output0.add("P2");
        Output0.add("P3");

        grdT0.Activations.add(new Activation(t0, reader, input0, TransitionOperation.FLRS, Output0));

        t0.GuardMappingList.add(grdT0);
        t0.Delay = 0;

        pn.Transitions.add(t0);

        // T1 ------------------------------------------------
        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "T1";

        t1.InputPlaceName.add("P2");

        Condition T1Ct1 = new Condition(t1, "P2", TransitionCondition.NotNull);

        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition = T1Ct1;


        ArrayList<PlaceNameWithWeight> input1 = new ArrayList<>();

        input1.add(new PlaceNameWithWeight("P2", 1F));

        ArrayList<String> Output1 = new ArrayList<>();

        Output1.add("P0");

        grdT1.Activations.add(new Activation(t1, OneXOneDefaultTable, input1, TransitionOperation.FLRS, Output1));

        t1.Delay = 1;
        t1.GuardMappingList.add(grdT1);

        pn.Transitions.add(t1);

        // T2 ------------------------------------------------
        PetriTransition t2 = new PetriTransition(pn);

        t2.TransitionName = "T2";
        t2.InputPlaceName.add("P3");

        Condition T2Ct1 = new Condition(t2, "P3", TransitionCondition.NotNull);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition = T2Ct1;

        ArrayList<PlaceNameWithWeight> input2 = new ArrayList<>();
        input2.add(new PlaceNameWithWeight("P3", 1F));

        ArrayList<String> Output2 = new ArrayList<>();
        Output2.add("P4");

        grdT2.Activations.add(new Activation(t2, t2FLRS, input2, TransitionOperation.FLRS, Output2));

        t2.Delay = 0;
        t2.GuardMappingList.add(grdT2);

        pn.Transitions.add(t2);

        // T3 ------------------------------------------------

        PetriTransition t3 = new PetriTransition(pn);
        t3.TransitionName = "T3";
        t3.InputPlaceName.add("P4");

        Condition T3Ct1 = new Condition(t3, "P4", TransitionCondition.NotNull);

        GuardMapping grdT3 = new GuardMapping();
        grdT3.condition = T3Ct1;

        ArrayList<PlaceNameWithWeight> input3 = new ArrayList<>();
        input3.add(new PlaceNameWithWeight("P4", 1F));

        ArrayList<String> Output3 = new ArrayList<>();
        Output3.add("P5");

        grdT3.Activations.add(new Activation(t3,OneXOneDefaultTable, input3, TransitionOperation.FLRS, Output3));


        t3.Delay = 0;
        t3.GuardMappingList.add(grdT3);

        pn.Transitions.add(t3);
        // -------------------------------------------

        pn.Delay = 500;
        pn.ShowLogInWindow = true;

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);


    }
}

