/**
 * 
 */
package net.obviam;

/**
 * This is the interface for all states. The methods are direct mappings
 * to actions that can happen in a Rifle.
 * 
 * @author impaler
 *
 */
public interface RifleState {

	public void insertClip();
	public void ejectClip();
	public void switchManualAuto();
	public void pullTrigger();
	public void fireAmmo();

}
