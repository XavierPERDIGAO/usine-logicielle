Application.views.Edit = Application.View.extend({
    dataTag: "edit",
    templateName: "edit",	
    render: function(){
        
    	/** Recover the id parameter from the URL */
    	var url = window.location.href;
    	var urlSplit = url.split("id=");  	
    	var taskId = urlSplit[urlSplit.length-1];
    	
    	/** Store the context to be able to use it later */
    	var self = this;

    	/** Ajax call to the API to recover data from the task whose id was given */
    	$.ajax({
    		url : "api/task-view/"+taskId,
    		success : function(task){
    			self.template({
    	    		title : task.title,
    	    		body : task.body
    	    	});
    			
    			/** On click on the edit button */
    	        $('#edit').click(function(event) {
    	        	event.preventDefault();
    	        	
    	        	/** Recover the values from the textareas */
    	        	var taskTitle = $('#title').val();
    	        	var taskBody = $('#description').val();

    	        	/** Ajax call to the API to edit the task */
    	        	$.ajax({
    	        		url : "api/task-edit",
    	        		type : "PUT",
    	        		data : JSON.stringify({
    	        			id : taskId,
    	        			title : taskTitle,
    	        			body : taskBody
    	        		}),
    	        		success : function(){
    	        			
    	        			/** Feedback to the user */
    	        			Application.flash("Tache modifiée avec succès");
    	        		},
    	        		error : function(){
    	        			
    	        			/** Feedback to the user */
    	        			Application.flash("Impossible de modifier la tâche", "danger");
    	        		}
    	        	});
    	        });
    		},
    		error : function(){
    			
    			/** Redirect the user to the task creation page */
    			window.location.href = "create";
    		}
    	});     
    }
});