package hellojpa;

public class ValueMain {

  public static void main(String[] args) {
    int a = 10;
    // a에 있는 값이 복사가 되어서 b에 할당된다.
    // a와 b는 완전히 따로 저장 공간을 가지고 있는 것이다.
    // 즉, 공유되지 않는다.
    int b = a;

    a = 20;

    // b는 20으로 변경되지 않는다.
    System.out.println("a = " + a);
    System.out.println("b = " + b);

    Integer c = 10;
    // c의 참조값만 넘어간다.
    Integer d = c;

    // setValue()는 없는 메서드지만 있다고 가정하면,
    // 레퍼런스를 넘겨서 같은 인스턴스를 공유하기 때문에 둘 다 값이 바뀐다.
//    c.setValue(20);

    System.out.println("c = " + c);
    System.out.println("d = " + d);
  }

}
