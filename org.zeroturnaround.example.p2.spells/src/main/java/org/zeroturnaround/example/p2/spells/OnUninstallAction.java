package org.zeroturnaround.example.p2.spells;

import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.internal.p2.engine.InstallableUnitOperand;
import org.eclipse.equinox.p2.engine.spi.ProvisioningAction;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;

@SuppressWarnings("restriction")
public class OnUninstallAction extends ProvisioningAction {

  @Override
  public IStatus execute(Map<String, Object> parameters) {
    IInstallableUnit upgradeTo = null;
    Object operand = parameters.get("operand");
    try {
      if (operand instanceof InstallableUnitOperand)
        upgradeTo = ((InstallableUnitOperand) operand).second();
    }
    catch (Throwable e) {
      // Ignore class not found in case InstallableUnitOperand is missing
    }
    if (upgradeTo == null) {
      // We have an unistallation, not an upgrade
      TxtFile txtFile = TxtFile.get();
      System.out.println("Deleting " + txtFile.path());
      txtFile.delete();
    }
    return Status.OK_STATUS;
  }

  @Override
  public IStatus undo(Map<String, Object> parameters) {
    return null;
  }

}
