package com.shiva.planner.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: PlannerDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun setUp(){
        database= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PlannerDatabase::class.java
        ).allowMainThreadQueries().build()
        taskDao=database.taskDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun addTaskTest()=runBlocking {
        val task=Task(id=1, title="test1", date= LocalDate.now().toString())
        taskDao.addTask(task)

        //val latch = CountDownLatch(1)
    //    val job = async(Dispatchers.IO) {
            val taskList=taskDao.getAllTasks().first()
            assertThat(taskList).contains(task)
    //.collect {
           //     assertThat(it).contains(task)
              //  latch.countDown()

          //  }
        }
      //  latch.await()
     //   job.cancelAndJoin()
  //  }

    @Test
    fun deleteTaskTest()=runBlocking {
        val task=Task(id=1, title="test1", date= LocalDate.now().toString())
        taskDao.addTask(task)
        taskDao.deleteTask(task)

        val taskList=taskDao.getAllTasks().first()
        assertThat(taskList).doesNotContain(task)

    }

    @Test
    fun updateTaskTest()=runBlocking {
        val task=Task(id=1, title="test1", date= LocalDate.now().toString())
        taskDao.addTask(task)
        task.title="updated"
        taskDao.updateTask(task)

        val taskFromDb=taskDao.getTask(1).first()
        assertThat(task).isEqualTo(taskFromDb)
        assertThat(task.title).isEqualTo(taskFromDb.title)

    }

    @Test
    fun getTaskTest()=runBlocking {
        val task=Task(id=1, title="test1", date= LocalDate.now().toString())
        taskDao.addTask(task)

        val returnedTask=taskDao.getTask(id=1).first()
        assertThat(returnedTask).isEqualTo(task)

    }

    @Test
    fun getTasksByDateTest()=runBlocking {
        val task1=Task(id=1, title="test1", date= LocalDate.now().toString())
        val task2=Task(id=2, title="test2", date="abcddf")

        taskDao.addTask(task1)
        taskDao.addTask(task2)

        val returnedTask=taskDao.getTasksByDate(date= LocalDate.now().toString()).first()
        assertThat(returnedTask[0]).isEqualTo(task1)

    }

    @Test
    fun getTasksByTypeAndDate()=runBlocking {
        val date="ddd"
        val task1=Task(id=1, title="a", date= date, isCompleted = true)
        val task2=Task(id=2, title="b", date=date, isCompleted = false)
        val task3=Task(id=3, title="c", date= LocalDate.now().toString(), isCompleted = true)
        val task4=Task(id=4, title="d", date=date, isCompleted = false)

        taskDao.addTask(task1)
        taskDao.addTask(task2)

        val completedTasks=taskDao.getTasksByTypeAndDate(isCompleted = true,date=date).first()
        val incompleteTasks=taskDao.getTasksByTypeAndDate(isCompleted = false,date=date).first()

        assertThat(completedTasks).contains(task1)
        assertThat(completedTasks).containsNoneOf(task2,task3,task4)
        assertThat(incompleteTasks).containsAnyOf(task2, task4)
        assertThat(incompleteTasks).containsNoneOf(task1,task3)

    }
}