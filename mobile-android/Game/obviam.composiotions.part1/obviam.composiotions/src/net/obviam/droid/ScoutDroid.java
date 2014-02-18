/**
 * 
 */
package net.obviam.droid;

import net.obviam.chassis.Wheels;
import net.obviam.weapon.LightLaserCanon;

/**
 * @author tamas
 *
 */
public class ScoutDroid extends Droid {

	public ScoutDroid() {
		id = "SCT-" + (++Droid.nextId); 
		weapon = new LightLaserCanon();
		chassis = new Wheels();
	}

	@Override
	public void display() {
		System.out.println("+--------------------------------------------------------------------------------------------+");
		System.out.println("| I am a SCOUT droid.");
		System.out.println("|\tID: " + id);
		System.out.println("|\tWeapon: " + weapon.getDescription());
		System.out.println("|\tChassis: " + chassis.getDescription());
		System.out.println("+--------------------------------------------------------------------------------------------+");
	}
	
}
