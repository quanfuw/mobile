/**
 * 
 */
package net.obviam;

/**
 * @author impaler
 *
 */
public class ManualFireState implements RifleState {
	
	private Rifle rifle;
	public ManualFireState(Rifle rifle) {
		this.rifle = rifle;
	}

	@Override
	public void ejectClip() {
		rifle.setAmmoCount(0);
		rifle.setState(rifle.getEmptyState());
		System.out.println("> Clip ejected. Please reload.");
	}

	@Override
	public void fireAmmo() {
		System.out.println("!* Pull the trigger for that!");
	}

	@Override
	public void insertClip() {
		System.out.println("!* Clip is already present!");
	}

	@Override
	public void pullTrigger() {
		System.out.println("> Pulled trigger.");
		rifle.setState(rifle.getAmmoFiredState());
		rifle.getState().fireAmmo();
	}

	@Override
	public void switchManualAuto() {
		rifle.setState(rifle.getAutoFireState());
		System.out.println("> Switched to auto. Bring'em on!!!");
	}

}
