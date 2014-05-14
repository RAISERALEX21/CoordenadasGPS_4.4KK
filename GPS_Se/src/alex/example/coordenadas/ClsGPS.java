package alex.example.coordenadas;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

/**
 * @author Alejandro Bautista M.
 * @version 1.0 08/05/2014
 * @since JDK 1.7
 */
public class ClsGPS extends Service implements LocationListener {

    private final Context mContext;

    // Bandera para GPS estatus
    private boolean isGPSEnabled = false;

    // Bandera para network estatus
    private boolean isNetworkEnabled = false;

    // Bandera para GPS estatus
    private boolean canGetLocation = false;

    Location location; // Location
    private double latitud;    // variable para Latitud
    private double longitud;   //  variable para Longitud
    private double altitud;    // variable para Altitud
    private String provider;   // variable para provider
    private double precision;
    //  La minima distancia para tomar los cambis de  Updates en metros
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 metros

    // Minimo tiempo de espera en milisegundos
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minuto

    // Declarando Location Manager
    protected LocationManager locationManager;

   public ClsGPS(Context context){
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // Tomando GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Tomadno network estatus
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // provedor de red esta enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                        	provider = location.getProvider();
                        	precision = location.getAccuracy();
                            latitud = location.getLatitude();
                            longitud = location.getLongitude();
                            altitud = location.getAltitude();
                        }
                    }
                }
                // Si GPS enabled, get latitude/longitude usando GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitud = location.getLatitude();
                                longitud = location.getLongitude();
                                provider = location.getProvider();
                            	precision = location.getAccuracy();
                                altitud = location.getAltitude();

                            	
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    /**
     * Detener el uso de GPS listener
     * Se llamara esta función para detener el uso de GPS en nuestra app.
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(ClsGPS.this);
        }
    }


    /**
     * Funcion para get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitud = location.getLatitude();
        }

        // return latitude
        return latitud;
    }


    /**
     * Funcion para get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitud = location.getLongitude();
        }

        // return longitude
        return longitud;
    }
    
    /**
     * Funcion para  get longitude
     * */
    public double getAltitude(){
    	if(location != null){
            longitud = location.getLongitude();
        }

        // return longitude
        return longitud;
    }
    
    /**
     * Funcion para  get provider
     * */
    public String getProveedor(){
    	if(location != null){
    		provider = location.getProvider();
    	}
    	
    	return provider;
    }
    
    /*
     * Funcion para  get precision
     */
   public double getPrecision(){
   	if(location != null){
   		precision = location.getAccuracy();
   	}
   	
   	return precision;
   }
    
    /**
     * Funcion para checar GPS/Wi-Fi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    /**
     * Funcino que mostrara las opciones de configuración/settings.
     * Al precionar la opción de configuración/settings nos enviara directo a las opciones de GPS.
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS Settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS no esta habilitado. ¿Deseas habilitarlo en Settings?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // Precionado button de canelar
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });

        // Mostrando mensaje de alerta
        alertDialog.show();
    }


    @Override
    public void onLocationChanged(Location location) {
    }


    @Override
    public void onProviderDisabled(String provider) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
	
}