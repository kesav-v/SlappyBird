import java.io.File;
import java.util.ArrayList;
/**
 * This program allows the user to search for files easily.
 * @author Ofek Gila
 * @version 1.0
 * @since  May 25th, 2015
 */
public final class FileSearcher	{

	/**
	 * Searches in current folder only
	 */
	public final static int CURRENT_FOLDER = 0;
	/**
	 * Searches current folder and subfolders
	 */
	public final static int SUBFOLDERS_AND_CURRENT = 1;
	/**
	 * Searches everything for the current user
	 */
	public final static int SEARCH_USER = 2;
	/**
	 * Searches EVERYTHING in the computer
	 */
	public final static int SEARCH_EVERYTHING = 3;
	/**
	 * The current directory as a file
	 */
	public final static File currentDirectory = new File(System.getProperty("user.dir"));

	private FileSearcher()	{}	// to prevent initialization

	private static File file = null;
	/**
	 * Finds the first instance of a file that matches the name
	 * @param  name       The name of the file with or without the extension
	 * @param  searchType The method of searching
	 * @return            The found File
	 */
	public static File findFile(String name, int searchType)	{
		return findFile(name, currentDirectory, searchType);
	}
	/**
	 * Finds the first instance of a file that matches the name
	 * @param  name          The name of the file with or without the extension
	 * @param  currentFolder The folder to start searching from if only searching currentfolder and or subfolders
	 * @param  searchType    The method of searching
	 * @return               The found File
	 */
	public static File findFile(String name, File currentFolder, int searchType)	{
		findFileR(getName(name), getExtension(name), getFolder(currentFolder, searchType), searchType);
		return file;
	}

	private static void findFileR(String name, String extension, final File folder, int searchType)	{
		try {
			for (final File fileEntry : folder.listFiles())
				if (file != null) return;
				else if (fileEntry.isDirectory() && !(searchType == CURRENT_FOLDER))
					findFileR(name, extension, fileEntry, searchType);
				else if (getName(fileEntry.getName()).equals(name) && (extension.equals("") || extension.equals(getExtension(fileEntry.getName()))))
					file = fileEntry;
		}	catch(NullPointerException e)	{}
	}
	/**
	 * Finds all the files that match the name
	 * @param  name       The name to find with or without the extension
	 * @param  searchType The method of searching
	 * @return            All the files found
	 */
	public static ArrayList<File> findFiles(String name, int searchType)	{
		return findFiles(name, currentDirectory, searchType);
	}
	/**
	 * Finds all the files that match the name
	 * @param  name          The name to find with or without the extension
	 * @param  currentFolder The folder to start searching from if only searching currentfolder and or subfolders
	 * @param  searchType    The method of searching
	 * @return               All the files found
	 */
	public static ArrayList<File> findFiles(String name, File currentFolder, int searchType)	{
		if (getName(name).equals("*"))
			return findFiles(currentFolder, searchType, getExtension(name));
		else return findFilesR(getName(name), getExtension(name), getFolder(currentFolder, searchType), searchType, new ArrayList<File>());
	}

	private static ArrayList<File> findFilesR(String name, String extension, final File folder, int searchType, ArrayList<File> files)	{
		try {
			for (final File fileEntry : folder.listFiles())
				if (fileEntry.isDirectory() && !(searchType == CURRENT_FOLDER))
					findFilesR(name, extension, fileEntry, searchType, files);
				else if (getName(fileEntry.getName()).equals(name) && (extension.equals("") || extension.equals(getExtension(fileEntry.getName()))))
					files.add(fileEntry);
		}	catch(NullPointerException e)	{}
		return files;
	}
	/**
	 * Finds all the files that have one of the given extensions
	 * @param  searchType The method of searching
	 * @param  extensions Any amount of Strings of extensions to search for (blank will find all)
	 * @return            All the files found
	 */
	public static ArrayList<File> findFiles(int searchType, String... extensions)	{
		return findFiles(currentDirectory, searchType, extensions);
	}
	/**
	 * Finds all the files that have one of the given extensions
	 * @param  currentFolder The folder to start searching from if only searching currentfolder and or subfolders
	 * @param  searchType    The method of searching
	 * @param  extensions    Any amount of Strings of extensions to search for (blank will find all)
	 * @return               All the files found
	 */
	public static ArrayList<File> findFiles(File currentFolder, int searchType, String... extensions)	{
		return findFilesR(getFolder(currentFolder, searchType), searchType, getExtensions(extensions), new ArrayList<File>());
	}

	private static ArrayList<File> findFilesR(final File folder, int searchType, String[] extensions, ArrayList<File> files)	{
		try {
			for (final File fileEntry : folder.listFiles())
				if (fileEntry.isDirectory() && !(searchType == CURRENT_FOLDER))
					findFilesR(fileEntry, searchType, extensions, files);
				else if (extensions.length == 0)
					files.add(fileEntry);
				else for (String extension : extensions)
					if (getExtension(fileEntry.getName()).equals(extension))	{
						files.add(fileEntry);
						break;
					}
		}	catch(NullPointerException e)	{}
		return files;
	}
	/**
	 * Prints the files with the given extensions
	 * @param searchType The method of searching
	 * @param extensions The extensions to search for
	 */
	public static void printFiles(int searchType, String... extensions)	{
		printFiles(currentDirectory, searchType, extensions);
	}
	/**
	 * Prints the files with the given extensions
	 * @param currentFolder The folder to start searching from if only searching currentfolder and or subfolders
	 * @param searchType    The method of searching
	 * @param extensions    The extensions to search for
	 */
	public static void printFiles(File currentFolder, int searchType, String... extensions)	{
		printFilesR(getFolder(currentFolder, searchType), searchType, getExtensions(extensions));
	}

	private static void printFilesR(final File folder, int searchType, String[] extensions)	{
		try {
			for (final File fileEntry : folder.listFiles())
				if (fileEntry.isDirectory() && !(searchType == CURRENT_FOLDER))
					printFilesR(fileEntry, searchType, extensions);
				else if (extensions.length == 0)
					System.out.println(fileEntry);
				else for (String extension : extensions)
					if (getExtension(fileEntry.getName()).equals(extension))	{
						System.out.println(fileEntry);
						break;
					}
		}	catch(NullPointerException e)	{}
	}
	/**
	 * Finds a folder with the given name
	 * @param  name       The name to search for
	 * @param  searchType The method of searching
	 * @return            A File represenation of the folder
	 */
	public static File findFolder(String name, int searchType)	{
		return findFolder(name, currentDirectory, searchType);
	}
	/**
	 * Finds a folder with the given name
	 * @param  name          The name to search for
	 * @param  currentFolder The folder to start searching from if only searching currentfolder and or subfolders
	 * @param  searchType    The method of searching
	 * @return               A File representation of the folder
	 */
	public static File findFolder(String name, File currentFolder, int searchType)	{
		findFolderR(name, getFolder(currentFolder, searchType), searchType);
		return file;
	}

	private static void findFolderR(String name, final File folder, int searchType)	{
		try {
			for (final File fileEntry : folder.listFiles())
				if (file != null) return;
				else if (fileEntry.isDirectory())
					if (fileEntry.getName().equals(name))
						file = fileEntry;
					else if (!(searchType == CURRENT_FOLDER))
						findFolderR(name, fileEntry, searchType);
		}	catch(NullPointerException e)	{}
	}
	/**
	 * Finds all folders with the given name
	 * @param  name       The name to search for
	 * @param  searchType The method of searching
	 * @return            The folders found
	 */
	public static ArrayList<File> findFolders(String name, int searchType)	{
		return findFolders(name, currentDirectory, searchType);
	}
	/**
	 * Finds all the folders with the given names
	 * @param  name          The name to search for
	 * @param  currentFolder The folder to start searching from if only searching currentfolder and or subfolders
	 * @param  searchType    The method of searching
	 * @return               The folders found
	 */
	public static ArrayList<File> findFolders(String name, File currentFolder, int searchType)	{
		return findFoldersR(name, getFolder(currentFolder, searchType), searchType, new ArrayList<File>());
	}

	private static ArrayList<File> findFoldersR(String name, final File folder, int searchType, ArrayList<File> folders)	{
		try {
			for (final File fileEntry : folder.listFiles())
				if (fileEntry.isDirectory())	{
					if (fileEntry.getName().equals(name))
						folders.add(fileEntry);
					if (!(searchType == CURRENT_FOLDER))
						findFoldersR(name, fileEntry, searchType, folders);
				}
		}	catch(NullPointerException e)	{}
		return folders;
	}

	private static File getFolder(File folder, int searchType)	{
		switch (searchType)	{
			case SEARCH_USER:
				while(folder.getParentFile().getParentFile().getParentFile() != null)
					folder = folder.getParentFile();
				return folder;
			case SEARCH_EVERYTHING:
				while(folder.getParentFile() != null)
					folder = folder.getParentFile();
				return folder;
			default: return folder;
		}
	}
	private static String getExtension(String name)	{
		return name.lastIndexOf(".") == -1 ? "":name.substring(name.lastIndexOf(".")).substring(1);
	}

	private static String getName(String name)	{
		return name.substring(name.lastIndexOf("\\")+1, name.lastIndexOf(".") == -1 ? name.length():name.lastIndexOf("."));
	}

	private static String[] getExtensions(String[] extensions)	{
		for (int i = 0; i < extensions.length; i++)
			if (!getExtension(extensions[i]).equals(""))
				extensions[i] = getExtension(extensions[i]);
		return extensions;
	}
}
