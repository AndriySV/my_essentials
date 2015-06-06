package chapter6.mvvm;

import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.ListModelList;

import entity.Priority;
import entity.Todo;

public class TodoListViewModel {

	 //data for the view
    String subject;
    ListModelList<Todo> todoListModel;
    Todo selectedTodo;
    
    public List<Priority> getPriorityList(){
        return Arrays.asList(Priority.values());
    }
    
    @Init // @Init annotates a initial method
    public void init(){
        //get data from service and wrap it to model for the view
//        List<Todo> todoList = todoListService.getTodoList();
        //you can use List directly, however use ListModelList provide efficient control in MVVM 
//        todoListModel = new ListModelList<Todo>(todoList);
    }
	
}
