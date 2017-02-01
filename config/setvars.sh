export VM_ARGS="$VM_ARGS -Dorg.osgi.framework.bootdelegation=mraa.*,upm_adc121c021.*,upm_buzzer.*,upm_grove.*"

EXTRA_CP=${EXTRA_CP}:/usr/lib/java/upm_grove.jar:/usr/lib/java/upm_buzzer.jar:/usr/lib/java/mraa.jar:/usr/lib/java/upm_adc121c021.jar
export EXTRA_CP

unset FWSECURITY
