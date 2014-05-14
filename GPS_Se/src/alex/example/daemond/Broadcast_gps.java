package alex.example.daemond;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * 
 * Generando que mi servicio Servicio_gps inicie desde que se prende el dispositivo.
 * 
 * */
public class Broadcast_gps extends BroadcastReceiver{
	
	@Override
	public void onReceive(Context context, Intent intent){
		Intent servicio = new Intent();
		servicio.setAction("alex.example.daemond.Servicio_gps");
		context.startService(servicio);
	}

}
