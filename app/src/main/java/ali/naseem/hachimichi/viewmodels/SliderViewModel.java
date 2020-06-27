package ali.naseem.hachimichi.viewmodels;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import ali.naseem.hachimichi.models.ImageModel;

public class SliderViewModel extends ViewModel {

    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("seatofresh");
    private MutableLiveData<ArrayList<ImageModel>> images = new MutableLiveData<>();
    private MutableLiveData<Integer> currentPage = new MutableLiveData<>(0);
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(true);

    public LiveData<ArrayList<ImageModel>> getImages() {
        return images;
    }

    public void loadImages() {
        ArrayList<ImageModel> models = new ArrayList<>();
        mStorageRef.listAll().addOnSuccessListener(listResult -> {
            StorageReference image1 = listResult.getItems().get(0);
            StorageReference image2 = listResult.getItems().get(1);
            Tasks.whenAllComplete(image1.getDownloadUrl(), image2.getDownloadUrl()).addOnSuccessListener(tasks -> {
                for (Task task : tasks) {
                    models.add(new ImageModel(Objects.requireNonNull(task.getResult()).toString()));
                }
                images.setValue(models);
                loading.setValue(false);
            });
        });
    }

    public LiveData<Integer> getCurrentPage() {
        return currentPage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void startTimer(int size) {
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (size > 0) {
                int val = currentPage.getValue() == null ? 0 : currentPage.getValue() + 1;
                if (val >= size) {
                    val = 0;
                }
                currentPage.setValue(val);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);
    }
}