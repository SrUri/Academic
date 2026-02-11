import java.util.concurrent.CompletableFuture;

public class FuturResultat extends CompletableFuture<Integer> {
    
    /**
     * Establece el resultado del cálculo futuro
     * @param resultat
     */

    public void setResultat (int resultat){
        super.complete(resultat);
    }

    /**
     * Establece la excepción del cálculo futuro
     * @param e
     */
    public void setException(Exception e) {
        super.completeExceptionally(e);
    }
}