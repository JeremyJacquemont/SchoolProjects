package com.lpiem.apps.loupelec.interfaces;

import android.os.Bundle;

import com.lpiem.apps.loupelec.utilities.MessageActivity;

/**
 * OnFragmentInteractionListener Interface
 */
public interface OnFragmentInteractionListener {

    /**
     * Communicate with Activity
     * @param message MessageActivity
     * @param params Bundle
     */
    public void onFragmentInteraction(MessageActivity message, Bundle params);
}
