package ru.education.service;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import ru.education.tower.resource.Resource;
import ru.education.unit.Worker;

public class WorkerService {
    private final Array<Resource> resourceArray;
    private final Array<Worker> workerArray;
    private final Array<Worker> activeWorkerArray;
    private float prevGenerateTime;

    public WorkerService(Array<Resource> resourceArray, Array<Worker> workerArray, Array<Worker> activeWorkerArray) {
        this.resourceArray = resourceArray;
        this.workerArray = workerArray;
        this.activeWorkerArray = activeWorkerArray;
        prevGenerateTime = 0f;
    }

    public void generateWorker(float curTime) {
        float dTime = curTime - prevGenerateTime;
        if (dTime > (new Random().nextInt(4) + 4f) && activeWorkerArray.size < workerArray.size) {
            activeWorkerArray.add(workerArray.get(activeWorkerArray.size));
            prevGenerateTime = curTime;
        }
    }

    public void workerClickProcessing(Vector3 touchPoint) {
        for (Worker worker : activeWorkerArray) {
            if (worker.contains(touchPoint.x, touchPoint.y)) {
                worker.clicked();
            } else {
                if (worker.getCurrentState() == Worker.StateWorker.CLICKED) {
                    boolean startWorking = false;

                    for (Resource resource : resourceArray) {

                        if (resource.contains(touchPoint.x, touchPoint.y)) {
                            worker.setWorkingPlace(resource);
                            worker.setCurrentState(Worker.StateWorker.GO_TO);
                            worker.setDestination(resource.getWorkBox());
                            startWorking = true;
                        }
                    }

                    if (!startWorking) worker.setCurrentState(Worker.StateWorker.SLEEP);
                }
            }
        }
    }
}
