package application;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
//import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class wirelessAssignmentController {
	
	public int city = 20;
	
	public int area = 20;

    @FXML
    private JFXTextField areaSize;

    @FXML
    private JFXTextField cellRadius;

    @FXML
    private JFXTextField reuseFactor;

    @FXML
    private JFXTextField alottedFrequency;

    @FXML
    private JFXButton problemOneCalculator;

    @FXML
    private ToggleGroup cellTypeRb;
    
    @FXML
    private JFXRadioButton macroRb;
    
    @FXML
    private JFXRadioButton microRb;

    @FXML
    private JFXTextField requiredCell;

    @FXML
    private JFXTextField channelsPerCell;

    @FXML
    private JFXTextField channelCapacity;

    @FXML
    private JFXTextField possibleConcurrentCall;

    @FXML
    private JFXTextField carrierFrequencyInput;

    @FXML
    private JFXTextField transmittingAntennaHeight;

    @FXML
    private JFXTextField receivingAntennaHeight;

    @FXML
    private JFXTextField antennaDistance;

    @FXML
    private ToggleGroup citySizeRb;
    
    @FXML
    private JFXRadioButton largeRb;
    
    @FXML
    private JFXRadioButton mediumRb;

    @FXML
    private JFXRadioButton smallRb;


    @FXML
    private ToggleGroup areaTypeRb;
    
    @FXML
    private JFXRadioButton urbanRb;
    
    @FXML
    private JFXRadioButton suburbanRb;

    @FXML
    private JFXRadioButton openRb;

    @FXML
    private JFXButton problemTwoCalculator;

    @FXML
    private JFXTextField problemTwoOutput;

    @FXML
    private Text close;

    @FXML
    void close(MouseEvent event) {
    	System.exit(0);

    }

    @FXML
    void problemOneCalculate(ActionEvent event) {
    	try {
    		Double area = Double.parseDouble(areaSize.getText());
    		Double radius = Double.parseDouble(cellRadius.getText());
    		Double factor = Double.parseDouble(reuseFactor.getText());
    		Double frequency = Double.parseDouble(alottedFrequency.getText());
    		String invalid = "Invalid input.";
    		boolean isValid = checkFactor(factor);
    	
    		Double required = (double) Math.round(area/(1.5*(Math.sqrt(3))*(Math.pow(radius, 2))));
    		Double numberOfChannelsPerCell = (double) Math.round(frequency/factor);
    		Double capacity = numberOfChannelsPerCell*required; 
    		Double concurrentCalls = capacity;
    	
    		areaSize.clear();
    		cellRadius.clear();
    		reuseFactor.clear();
    		alottedFrequency.clear();
    		
    		if(isValid == true) {
    			setResultProblemOne (required, numberOfChannelsPerCell, capacity, concurrentCalls );
    		} else {
    			setResultInvalidProblemOne (invalid);
    		}
    		
    	    
    	} catch (Exception e) {
    		//Exception
    		
    	}
    	
    	
    	
    }
    
    private boolean checkFactor(Double factor){
        for(int i=0;i<10000;i++){
            for(int j=0;j<10000;j++){
                long val = (long) 0 + i*i + i*j + j*j ;
                if(val == factor) return true;
            }
        }
        return false;
    }
    
    void setResultInvalidProblemOne (String invalid) {
    	requiredCell.setText(invalid.toString());
    	channelsPerCell.setText(invalid.toString());
    	channelCapacity.setText(invalid.toString());
    	possibleConcurrentCall.setText(invalid.toString());
    }
    
    void setResultProblemOne (Double required, Double numberOfChannelsPerCell, Double capacity, Double concurrentCalls ) {
    	requiredCell.setText(required.toString());
    	channelsPerCell.setText(numberOfChannelsPerCell.toString());
    	channelCapacity.setText(capacity.toString());
    	possibleConcurrentCall.setText(concurrentCalls.toString());
    }

    @FXML
    void problemTwoCalculate(ActionEvent event) {
    	try {
    		Double fc = Double.parseDouble(carrierFrequencyInput.getText());
    		Double ht = Double.parseDouble(transmittingAntennaHeight.getText());
    		Double hr = Double.parseDouble(receivingAntennaHeight.getText());
    		Double d = Double.parseDouble(antennaDistance.getText());
    		String inv = "Invalid input.";
    		//cityRadioSelect(event);
    		int citySize = city;
    		int areaType = area;
    		
    		Double Ahr;
    		Double Loss = (double) -20;
    		
    		
    		Double log_fc = Math.log10(fc);
    		Double log_ht = Math.log10(ht);
    		Double log_d = Math.log10(d);
    		
    		if(citySize == 2 || citySize ==3) {
    			Ahr = ((((1.1*log_fc)-0.7)*hr)-((1.56*log_fc)-0.8));
    		}else {
    			if(fc <= 300) {
    				
    				Ahr = (((Math.pow((Math.log10((1.54*hr))),2))*8.29)-1.1); 
    				
    			}else {
    				
    				Ahr = (((Math.pow((Math.log10((11.75*hr))),2))*3.2)-4.97);
    				
    			}
    		}
    		
    		System.out.println(Ahr);
    		
    		
    		Double L = (69.55 + (26.16*log_fc) - (13.82*log_ht) - Ahr + ((44.9 - (6.55*log_ht))*log_d));
    		System.out.println(areaType);
    		System.out.println(citySize);
    		if(areaType == 1) {
    			Loss = L;
    		}else if(areaType == 2) {
    			Loss = (L - (2*(Math.pow((Math.log10((fc/28))),2)))-5.4);
    		}else if(areaType == 3) {
    			Loss = (L - (4.78*(Math.pow(log_fc,2))) -(18.733*log_fc) -40.98);
    			System.out.println("g");
    		}
    		
    		carrierFrequencyInput.clear();
    		transmittingAntennaHeight.clear();
    		receivingAntennaHeight.clear();
    		antennaDistance.clear();
    		
    		
    		setResultProblemTwo(Loss);
    		
    		if((fc>=150 && fc<=1500) && (ht>=30 && ht<=300) && (hr>= 1 && hr<=10) && (d>=1 && d<=20)) {
    			setResultProblemTwo(Loss);
    		} else {
    			setResultInvalidProblemTwo (inv);
    		}
    		
    		
    	} catch (Exception e) {
    		//Exception
    		
    	}
    	

    }
    
    void setResultProblemTwo (Double Loss) {
    	problemTwoOutput.setText(Loss.toString());
    }
    
    void setResultInvalidProblemTwo (String inv) {
    	problemTwoOutput.setText(inv.toString());
    }
    
    public void cityRadioSelect(ActionEvent event) {

    	if(largeRb.isSelected()) {
    		city = 1 ;
    	}
    	if(mediumRb.isSelected()) {
    		city = 2;
    	}
    	
    	if(smallRb.isSelected()) {
    		city = 3;
    	}
    }
    
    public void areaRadioSelect(ActionEvent event) {
    	if(urbanRb.isSelected()) {
    		area = 1 ;
    	}
    	if(suburbanRb.isSelected()) {
    		area = 2;
    	}
    	
    	if(openRb.isSelected()) {
    		area = 3;
    	}
    }

}
