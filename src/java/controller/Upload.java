package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;

@ManagedBean
@ViewScoped
public class Upload implements Serializable
{

  public Upload()
  {
  }

  public void excelUpload(FileUploadEvent event) throws IOException
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();

    String realPath = servletContext.getRealPath("/");
    File dir = new File(realPath + "/import/");
    dirExists(dir);

    FileInputStream fileInputStream = (FileInputStream) event.getFile().getInputstream();
    byte[] vArquivoByte = new byte[(int) event.getFile().getSize()];
    String fileName = realPath + "/import/" + event.getFile().getFileName();
    fileStream(fileInputStream, vArquivoByte, fileName);
    
    FacesMessage messageUpload = new FacesMessage("[" + event.getFile().getFileName() + "]", "Salvo com sucesso!");
    facesContext.addMessage(null, messageUpload);
  }

  public void fileStream(FileInputStream fileInputStream, byte[] arquivoByte, String fileName) throws IOException
  {
    fileInputStream.read(arquivoByte);

    try (FileOutputStream fileOutputStream = new FileOutputStream(fileName))
    {
      fileOutputStream.write(arquivoByte);
      fileOutputStream.flush();
    }
  }

  public boolean dirExists(File dir)
  {
    if (dir.exists())
    {
      return true;
    }
    else
    {
      dir.mkdirs();
      return false;
    }
  }

}
