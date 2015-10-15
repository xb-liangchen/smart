package test.client;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ClientTest {

public static void main(String[] args) {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension scrnsize = toolkit.getScreenSize();
    System.out.println ("Screen size : " + scrnsize.width + " * " + scrnsize.height);
}
}
