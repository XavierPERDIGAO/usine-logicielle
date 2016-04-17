Application.views.Show = Application.View.extend({
    dataTag: "show",
    templateName: "show",	
    render: function(){
        
    	/** Helper to return the edit url corresponding to the ID */
		Handlebars.registerHelper('edit_url', function(id){
			return "edit?id=" + id;
		});
		
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
    			
    			/** Fill the template with the task details */
    	    	self.template({
    	    		id : taskId,
    	    		title : task.title,
    	    		body : task.body
    	    	});
    	    	
    			/** On click on the delete button */
    			$('.btn-danger').click(function(event) {
    				event.preventDefault();

    				/** Ask confirmation to user */
    				if (confirm("Delete this task ? ")) {			
 
    				/** Ajax call to the API to delete the task */
    				$.ajax({
    					url : "api/task-delete/" + taskId,
    					type : 'DELETE',
    					success : function() {
    						
    						/** Redirect the user to the tasks page */
    						window.location.href = "tasks";
    					},
    	                error: function(){
    	                	
    	                	/** User feedback */
    	                    Application.flash("Impossible de supprimer la tâche", "danger");
    	                }
    				});
    				}
    			});
    		},
    		error : function(){
    			
    			/** Redirect the user to the tasks page */
    			window.location.href = "tasks";
    		}
    	});		
    }
});