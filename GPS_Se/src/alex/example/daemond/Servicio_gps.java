package alex.example.daemond;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import alex.example.coordenadas.ClsGPS;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

public class Servicio_gps  extends Service{
	
	private static Servicio_gps instance;

	private Timer dataTimer = null;
	public String usuario;
	public int TIEMPO_REPETICION = 2000*60;
	
	
	public static boolean isRunning(){
		return instance != null;
	}
	
	@Override
	public IBinder onBind(Intent intent){
		return null;
	}
	
	
	@Override
	public void onCreate(){
		
		super.onCreate();		
		instance = this;	
		
	}
	
	@Override
	public void onStart(final Intent intent, int startID){
		
		System.out.println("SERVICIO GPS iniciado");	
				
		this.dataTimer = new Timer();
		this.dataTimer.scheduleAtFixedRate(
		new TimerTask(){
		
			@Override
			public void run(){
								
				Thread thread = new Thread(new Runnable(){
					
					@Override
					@SuppressLint({ "HandlerLeak", "ShowToast" })
					public void run(){
						
						Looper.prepare();
						
							ClsGPS mostrar_coord = new ClsGPS(getApplicationContext());
						
							//TOMANDO FECHA INTERNA DE DISPOSITIVO...
						
							Date fecha = new Date();
							String mi_fecha = fecha.getHours() + "hrs " +  fecha.getMinutes() + "min " + fecha.getSeconds() + "seconds " ;
						
							//GENERANDO MI MENSAJE!!!...............
						
							Toast.makeText(getApplicationContext(), " FECHA: \n"+ mi_fecha +"\n" +"Mostrando coordenadas: \n  Latitud: " + mostrar_coord.getLatitude() + "\n Longitud: " + mostrar_coord.getLongitude(), Toast.LENGTH_LONG).show();
							
						
						Looper.loop();
						
					}
				});
				
				thread.start();	
		
			}
			
		}
		, 0, TIEMPO_REPETICION);//CONSTANTE EN TIEMPO DE REPETICIÓN
		
	}	
	
	@Override
	public void onDestroy() {	
		    super.onDestroy();
	}

}