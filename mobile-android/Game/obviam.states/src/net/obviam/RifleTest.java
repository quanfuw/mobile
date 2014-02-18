/**
 * 
 */
package net.obviam;

/**
 * @author impaler
 *
 */
public class RifleTest {

	public static void main(String[] args) {
		Rifle rifle = new Rifle();
		
		rifle.pullTrigger();
		rifle.ejectClip();
		rifle.insertClip();
		rifle.pullTrigger();
		rifle.switchManualAuto();
		rifle.pullTrigger();
		rifle.pullTrigger();
		rifle.switchManualAuto();
		rifle.pullTrigger();
		rifle.insertClip();
		rifle.switchManualAuto();
		rifle.pullTrigger();
		rifle.pullTrigger();
		rifle.pullTrigger();
		rifle.pullTrigger();
		rifle.pullTrigger();
	}

}
