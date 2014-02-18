/**
 * 
 */
package net.obviam;

/**
 * @author impaler
 *
 */
public class RifleOldTest {
	
	public static void main(String[] args) {
		RifleOld rifle = new RifleOld();
		System.out.println(rifle);
		
		rifle.insertClip();
		rifle.pullTrigger();
		rifle.pullTrigger();
		rifle.pullTrigger();
		rifle.pullTrigger();
		
		System.out.println(rifle);
		
		rifle.insertClip();
		rifle.ejectClip();
		rifle.pullTrigger();
	}
}
