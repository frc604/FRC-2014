package com._604robotics.robotnik.action.controllers;

import com._604robotics.robotnik.action.ActionController;

public class StateController extends ActionController {
    protected String pickAction (String lastAction, String triggeredAction) {
        return triggeredAction.equals("")
                   ? lastAction.equals("")
                       ? this.getDefaultAction()
                       : lastAction
                   : triggeredAction;
    }
}