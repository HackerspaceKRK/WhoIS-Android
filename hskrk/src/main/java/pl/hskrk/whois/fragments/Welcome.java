package pl.hskrk.whois.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.hskrk.whois.ConfigHSLightsManager;
import pl.hskrk.whois.ConfigPresenceDisplayer;
import pl.hskrk.whois.R;
import pl.hskrk.whois.widget.HSLightsManager;
import pl.hskrk.whois.widget.PresenceDisplayer;

/**
 * Created by Filip on 02.01.14.
 */
public class Welcome extends Fragment {
    public Welcome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Button configurePresence = (Button)rootView.findViewById(R.id.button_presence_disp);
        Button configureLights = (Button)rootView.findViewById(R.id.button_lights_manager);

        configurePresence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new  Intent(getActivity(), ConfigPresenceDisplayer.class);
                startActivity(i);
            }
        });

        configureLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new  Intent(getActivity(), ConfigHSLightsManager.class);
                startActivity(i);
            }
        });

        return rootView;
    }
}
