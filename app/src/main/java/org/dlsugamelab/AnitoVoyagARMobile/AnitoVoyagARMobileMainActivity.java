package org.dlsugamelab.AnitoVoyagARMobile;

import org.libsdl.app.SDLActivity;

public class AnitoVoyagARMobileMainActivity extends SDLActivity {
    @Override
    protected String[] getLibraries() {
        return new String[] {
                "SDL3",
                "Anito-VoyagAR"
        };
    }
}
