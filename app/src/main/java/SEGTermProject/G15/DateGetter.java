package SEGTermProject.G15;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import okhttp3.internal.cache.DiskLruCache;

public class DateGetter {
    private Task<QuerySnapshot>  myTask;
    public QuerySnapshot QS;

    DateGetter(Task<QuerySnapshot> task){
        myTask = task;
        GenerateSnapShot();
    }

    private void GenerateSnapShot(){
        myTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> qs) {
                getSnapShot(qs.getResult());
            }
        });

    }

    private void getSnapShot(QuerySnapshot SN){
        QS = SN;
    }
}
