package programas;

import java.util.Arrays;

public class LibreriasCrisol {
    public static void main(String[] args) {

        String codigosClientes[] = {"c001","c002","c003","c004","c005","c006","c007","c008","c009","c010"};
        int mesesUltCompra[] = {1,2,1,1,2,3,3,5,6,7}; //meses transcurridos desde su última compra
        int frecuenciaCompra[] = {1,3,2,4,5,3,2,6,8,5}; //clientes compra cada X meses
        double montoCompraAcum[] = {100.57,250.34,57.31,135.55,300.78,86.44,462.99,705.5,500.5,299.4}; //compra acumulada

        //****************************************************
        //Prueba de Asignación de Puntajes RFM a toda la data
        //****************************************************

        int puntajeR[] = asignarPuntajeRecency(mesesUltCompra);
        System.out.println("Puntajes Recency: " + Arrays.toString(puntajeR));

        int puntajeF[] = asignarPuntajeFrecuency(frecuenciaCompra);
        System.out.println("Puntajes Frecuency: " +Arrays.toString(puntajeF));

        int puntajeM[] = asignarPuntajeMonetary(montoCompraAcum);
        System.out.println("Puntajes Monetary: " + Arrays.toString(puntajeM));

        //*************************************************
        //Prueba de Asignación de Segmentos a toda la data
        //*************************************************

        char segmentos[] = asignarSegmentos(mesesUltCompra,frecuenciaCompra,montoCompraAcum);
        System.out.println("Segmentos: " + Arrays.toString(segmentos));


        //***********************************************************************************************************
        //Prueba de cálculo de precio final aplicando descuento diferenciado según segmento de un cliente específico
        //***********************************************************************************************************

        String idCliente = "c001";
        int cantidadProductos = 2;
        double precioSinIGV = 85.56;
        double precioFinal = calcularPrecioFinal(idCliente,cantidadProductos,precioSinIGV,codigosClientes,mesesUltCompra,frecuenciaCompra,montoCompraAcum);
        System.out.println("Precio final con IGV es: " + precioFinal + " soles");
    }

    public static int[] asignarPuntajeRecency (int[] mesesUltCompra) {
        int puntaje[] = new int[mesesUltCompra.length];

        for (int i = 0;i < mesesUltCompra.length;i++) {
            if (mesesUltCompra[i]==1) {
                puntaje[i] = 5;
            } else if (mesesUltCompra[i]>=2 && mesesUltCompra[i]<=3) {
                puntaje[i] = 4;
            } else if (mesesUltCompra[i]>=4 && mesesUltCompra[i]<=6) {
                puntaje[i] = 3;
            } else if (mesesUltCompra[i]>=7 && mesesUltCompra[i]<=12) {
                puntaje[i] = 2;
            } else if (mesesUltCompra[i]>12) {
                puntaje[i] = 1;
            }
        }
        return puntaje;
    }

    public static int[] asignarPuntajeFrecuency (int[] frecuenciaCompra) {
        int puntaje[] = new int[frecuenciaCompra.length];

        for (int i = 0;i < frecuenciaCompra.length;i++) {
            if (frecuenciaCompra[i]==1) {
                puntaje[i] = 5;
            } else if (frecuenciaCompra[i]==2) {
                puntaje[i] = 4;
            } else if (frecuenciaCompra[i]==3) {
                puntaje[i] = 3;
            } else if (frecuenciaCompra[i]>=4 && frecuenciaCompra[i]<=6) {
                puntaje[i] = 2;
            } else if (frecuenciaCompra[i]>=7) {
                puntaje[i] = 1;
            }
        }
        return puntaje;
    }

    public static int[] asignarPuntajeMonetary (double[] montoCompraAcum) {
        int puntaje[] = new int[montoCompraAcum.length];

        for (int i = 0;i < montoCompraAcum.length;i++) {
            if (montoCompraAcum[i]>300) {
                puntaje[i] = 5;
            } else if (montoCompraAcum[i]>=200 && montoCompraAcum[i]<=300) {
                puntaje[i] = 4;
            } else if (montoCompraAcum[i]>=100 && montoCompraAcum[i]<=199) {
                puntaje[i] = 3;
            } else if (montoCompraAcum[i]>=50 && montoCompraAcum[i]<=99) {
                puntaje[i] = 2;
            } else if (montoCompraAcum[i]<50) {
                puntaje[i] = 1;
            }
        }
        return puntaje;
    }

    public static char[] asignarSegmentos (int[] mesesUltCompra, int[] frecuenciaCompra, double[] montoCompraAcum) {
        int puntajeR[] = asignarPuntajeRecency(mesesUltCompra);
        int puntajeF[] = asignarPuntajeFrecuency(frecuenciaCompra);
        int puntajeM[] = asignarPuntajeMonetary(montoCompraAcum);
        char[] segmentos = new char[mesesUltCompra.length];
        String concatenacion;
        int combinacionPuntajes;

        for (int i = 0;i < mesesUltCompra.length;i++) {
            concatenacion = String.valueOf(puntajeR[i]) + String.valueOf(puntajeF[i]) + String.valueOf(puntajeM[i]);
            combinacionPuntajes = Integer.valueOf(concatenacion);

            if (combinacionPuntajes >=500 && combinacionPuntajes <=555) {
                segmentos[i] = 'A';
            } else if (combinacionPuntajes >=400 && combinacionPuntajes <=499) {
                segmentos[i] = 'B';
            } else
                segmentos[i] = 'C';
        }
        return segmentos;
    }

    public static double calcularPrecioFinal (String idCliente, int cantidadProductos, double precioSinIgv, String[] codigosClientes, int[] mesesUltCompra,
                                              int[] frecuenciaCompra, double[] montoCompraAcum) {
        int indexCliente = 0;
        for (int i = 0; i < codigosClientes.length; i++) {
            if (idCliente.equals(codigosClientes[i])) indexCliente = i;
        }

        char segmentos[] = asignarSegmentos(mesesUltCompra,frecuenciaCompra,montoCompraAcum);
        double descuento = 0.00;

        switch (segmentos[indexCliente]) {
            case 'A': descuento = 0.2; break;
            case 'B': descuento = 0.1; break;
            case 'C': descuento = 0.05; break;
            default: descuento = 0.00; break;
        }
        double precioFinal = ((cantidadProductos * precioSinIgv) * (1 - descuento)) * 1.18;
        return Math.round(precioFinal*100.0)/100.0;
    }
}