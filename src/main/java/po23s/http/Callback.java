package po23s.http;

public interface Callback<E> {
    void onDone(E result, Exception erro);
}

