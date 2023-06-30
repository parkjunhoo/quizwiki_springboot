
	    tinymce.init({
	      menubar: false,	
	      plugins: 'link lists media image visual blocks ',
	      toolbar: 'alignleft aligncenter alignright alignjustify| undo redo| styles | bold italic underline |formatselect | bullist numlist | outdent indent | link image ',
	      toolbar_mode: 'floating',
	      content_style: 'body{font-family:Helvetica,Arial,sans-serif; font-size:16px}',
	      tinycomments_mode: 'embedded',
	      tinycomments_author: 'Author name',
	      mergetags_list: [
	        { value: 'First.Name', title: 'First Name' },
	        { value: 'Email', title: 'Email' },
	      ],
	      images_upload_handler: function (blobInfo) {
	    	  imageList.push(blobInfo.blob());
	      },
	      
	      /* enable title field in the Image dialog*/
	      image_title: true,
	    
	      /* enable automatic uploads of images represented by blob or data URIs*/
	      automatic_uploads: true,
	                            
	      /*Here we add custom filepicker only to Image dialog*/
	      file_picker_types: 'image',
	    
	      /* and here's our custom image picker*/
	      file_picker_callback: function (cb, value, meta) {
	          var input = document.createElement('input');
	          input.setAttribute('type', 'file');
	          input.setAttribute('accept', 'image/*');
	  
	          input.onchange = function () {
	              var file = this.files[0];                    
	              var reader = new FileReader();
	              
	              reader.onload = function () {
	                  /*
	                  Note: Now we need to register the blob in TinyMCEs image blob
	                  registry. In the next release this part hopefully won't be
	                  necessary, as we are looking to handle it internally.
	                  */
	                  var id = 'blobid' + (new Date()).getTime();
	                  var blobCache =  tinymce.activeEditor.editorUpload.blobCache;
	                  var base64 = reader.result.split(',')[1];
	                  
	                  var blobInfo = blobCache.create(id, file, base64);                                    
	                  blobCache.add(blobInfo);
	  
	                  /* call the callback and populate the Title field with the file name */
	                  cb(blobInfo.blobUri(), { title: file.name });
	              };
	        
	              reader.readAsDataURL(file);
	          };
	  
	          input.click();
	      }     
	    });
  