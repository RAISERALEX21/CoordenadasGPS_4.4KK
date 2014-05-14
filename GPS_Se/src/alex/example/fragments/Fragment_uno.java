package alex.example.fragments;

import alex.example.gps_se.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_uno  extends Fragment{
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
	return inflater.inflate(R.layout.fragment_uno, container, false);
	
}
}
