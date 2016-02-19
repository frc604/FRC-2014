package com._604robotics.robotnik.meta;

public class Scorekeeper {
    public Object victor = null;
    public int count = 0;
    
    private final double base;
    public double score;
    
    public Scorekeeper (double base) {
        this.base = this.score = base;
    }
    
    public void consider (Object item, double score) {
        if (this.victor == null || score > this.score) {
            this.victor = item;
            this.score = score;
        }
        
        this.count++;
    }
    
    public void reset () {
        this.victor = null;
        this.count = 0;
        
        this.score = this.base;
    }
}
