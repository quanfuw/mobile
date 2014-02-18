/**
 * 
 */
package net.obviam.droid;

import net.obviam.chassis.Track;
import net.obviam.weapon.HeavyLaserCanon;

/**
 * A heavy assault droid with the heavy laser canon.
 * 
 * @author impaler
 *
 */
public class AssaultDroid extends Droid {

	public AssaultDroid() {
		id = "ASS-" + (++Droid.nextId); 
		weapon = new HeavyLaserCanon();
		chassis = new Track();
	}
	
	@Override
	public void display() {
		System.out.println("+--------------------------------------------------------------------------------------------+");
		System.out.println("| I am an ASSAULT droid.");
		System.out.println("|\tID: " + id);
		System.out.println("|\tWeapon: " + weapon.getDescription());
		System.out.println("|\tChassis: " + chassis.getDescription());
		System.out.println("+--------------------------------------------------------------------------------------------+");
	}
}
