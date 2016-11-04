package com._604robotics.robotnik.action.controllers;

import com._604robotics.robotnik.action.ActionController;

public class ElasticController extends ActionController {
    protected String pickAction (String lastAction, String triggeredAction) {
        if (triggeredAction.equals("")) {
            return this.getDefaultAction();
        } else {
            return triggeredAction;
        }
    }
}
