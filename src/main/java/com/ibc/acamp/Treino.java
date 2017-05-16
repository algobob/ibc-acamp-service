package com.ibc.acamp;

import static spark.Spark.get;

public class Treino {
    public static void main(String[] args) {
        get("/",(req,res) -> "teste!!!");
    }
}
