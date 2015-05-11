/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rouletteex1;

import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author Shay
 */
public class RouletteNumber {

    // ENUM
    public enum RouletteNumberColor {

        GREEN, RED, BLACK
    }

    // VARS
    private String _number;
    private RouletteNumberColor _color;

    // CTOR
    public RouletteNumber(String number, RouletteNumberColor color) {
        this._number = number;
        this._color = color;
    }

    // GETTERS
    public String GetName() {
        return _number;
    }

    // FUNCS
    public RouletteNumberColor GetColor() {
        return _color;
    }

    // OVERRIDE
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this._number);
        hash = 29 * hash + Objects.hashCode(this._color);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RouletteNumber other = (RouletteNumber) obj;
        if (!Objects.equals(this._number, other._number)) {
            return false;
        }
        if (this._color != other._color) {
            return false;
        }
        return true;
    }
}
