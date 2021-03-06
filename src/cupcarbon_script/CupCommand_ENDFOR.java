/*----------------------------------------------------------------------------------------------------------------
 * CupCarbon: A Smart City & IoT Wireless Sensor Network Simulator
 * www.cupcarbon.com
 * ----------------------------------------------------------------------------------------------------------------
 * Copyright (C) 2013-2017 CupCarbon
 * ----------------------------------------------------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *----------------------------------------------------------------------------------------------------------------
 * CupCarbon U-One is part of the research project PERSEPTEUR supported by the
 * French Agence Nationale de la Recherche ANR
 * under the reference ANR-14-CE24-0017-01.
 * ----------------------------------------------------------------------------------------------------------------
 **/

package cupcarbon_script;

/**
 * @author Ahcene Bounceur
 * @author Molham Darwish
 * @version 1.0
 */

public class CupCommand_ENDFOR extends CupCommand {
	
	protected boolean trueCondition = true ;	
	protected int index ;

	public CupCommand_ENDFOR(CupScript script) {
		this.script = script ;
	}

	@Override
	public String execute() {		
		String n = getCurrentFor().getRight();
		String variable = getCurrentFor().getLeft();
		
		CupCommand_FOR commandFor = getCurrentFor();
		
		String v1 = script.getVariableValue(variable);		
		String v2 = script.getVariableValue(""+commandFor.getStep());		
		double z = 0;
		z = Double.valueOf(v1) + Double.valueOf(v2);
		script.addVariable(variable.substring(1, variable.length()), ""+z);
		
		CupCondition condition = null;
		if(commandFor.getStep()>=0)
			condition = new CupCondition_LESS(script, variable, n);
		else 
			condition = new CupCondition_GREATER(script, variable, n);
		trueCondition = condition.evaluate();		
		
		if (trueCondition) {
			script.setIndex(commandFor.getIndex()-1);
		}
		else {
			commandFor.init();
		}
		if(trueCondition)
			return "001";
		else
			return "000";
	}
	
	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}

	
	public boolean isTrueCondition() {
		return trueCondition;
	}
	
	@Override
	public String toString() {
		return "ENDFOR";
	}
}
