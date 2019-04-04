package com.sydml.authorization;

/**
 * @author Liuym
 * @date 2019/3/29 0029
 */
public class Calcu {
    public static void main(String[] args) {

        Double[] strongElec = {2.2d, 3.9d, 2.1d, 1d, 1.4d, 1.9d, 1.5d, 1.1d, 4d, 3d, 1.8d, 1.1d, 2.4d, 4.4d, 2.2d, 1.8d, 1.2d,
                2.3d, 0.9d, 1.2d, 0.8d, 0.5d, 1.8d, 2.8d, 2.6d, 2.6d, 2.6d, 2.8d, 3d, 2.8d, 0.3d, 2.2d, 1.3d,
                2.8d, 2.1d, 2.4d, 3.7d, 3.7d, 3.7d, 3.7d, 3d, 1.3d, 1.1d, 0.8d, 4.2d, 2d, 1.6d, 1.4d, 0.6d, 0.6d, 2.2d};

        Double[] weakElec = {1.4d,3.5d,2.5d,0.7d,2.7d,2.5d,5d,0.9d,1.9d,0.9d,0.7d,4.3d,1.4d,0.9d,0.9d,0.9d,0.9d,0.9d,0.9d,
                2.9d,4.1d,3.4d,1.3d,0.6d,2.2d,2.3d,2.1d,1.4d,2.1d,2.5d,2.2d,1.4d,1d,0.8d,1.2d,
                1.4d,0.6d,2.5d,1.1d,2.5d,8.5d,6d
        };
        Double sumWeak=0d;
        for (Double aDouble : weakElec) {
            sumWeak+=aDouble;
        }
        System.out.println(sumWeak);
        Double sumStr = 0d;
        for (Double aDouble : strongElec) {
            sumStr+=aDouble;
        }
        System.out.println(sumStr);
    }

}
