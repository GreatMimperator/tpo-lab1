package ru.miron.func;

public class MyMath {

    // formula from https://otvet.imgsmail.ru/download/651f2113e15fffe25c1776436be846be_i-46.jpg
    public static double asin(double x, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("\"n\" should be >= 0");
        }
        double answer = x;
        // (1*3*5*...*(2n-1)) / (2*4*6*...*2n) * (x^(2n+1)) / (2n+1)
        // leftUp / leftDown * rightUp / rightDown
        // left * right
        double left = 1;
        double rightUp = x;
        for (double i = 1; i <= n; i++) {
            left *= (2*i - 1) / (2*i);
            rightUp *= x*x;
            answer += left * rightUp / (2*i + 1);
        }
        return answer;
    }
}
