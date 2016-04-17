Application.views.Create = Application.View.extend({
    dataTag: "create",
    templateName: "create",	
    render: function(){
    	
        this.template();
        
        /** On click on the create button */
        $('#create').click(function(event) {
        	event.preventDefault();
        	
        	/** Recover the title and the body from the textareas */
        	var taskTitle = $('#title').val();
        	var taskBody = $('#description').val();

        	/** Ajax call */
        	$.ajax({
        		url : "api/task-create",
        		type : "POST",
        		data : JSON.stringify({
        			title : taskTitle,
        			body : taskBody
        		}),
        		success : function(){
        			
        			/** Redirect the user to the tasks list */
        			window.location.href = "tasks";
        		},
        		error : function(){
        			Application.flash("Impossible de créer la tâche", "danger");
        		}
        	});

        });
        
    }
});