package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    // 자기 자신 인스턴스 생성해서 반환
    public static SingletonService getInstance(){
        return instance;
    }
    
    // private으로 외부에서 언금
    private SingletonService() {
        
    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
