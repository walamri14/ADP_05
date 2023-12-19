package application;


import java.io.Serializable;
import java.util.List;

import supportStuff.applicationSupport.Coordinate;
import supportStuff.utility.Version;


/**
 * Task: For information see ReadMe.txt resp. task
 * This is just a demo. It shows an easy way to support multiple alternatives.
 * 
 * @version {@value #encodedVersion}
 * @author  Michael Schaefers  ([UTF-8]:"Michael Schäfers");
 *          Px@Hamburg-UAS.eu 
 */
public class PathFinder implements PathFinder_I, Serializable {
    //
    //--VERSION:-----------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                       #___~version~___YYYY_MM_DD__dd_
    final static long encodedVersion = 2___00001_001___2021_01_05__02L;
    //---------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    final static Version version = new Version( encodedVersion );
    static String getDecodedVersion(){ return version.getDecodedVersion(); }
    //
    static final private long serialVersionUID = version.getVersionNumber();
    // Obiges (ab VERSION) dient nur der Versionierung
    
    
    
    
    
    final private application.PathFinder_I theActualPathFinder;
    
    
    
    /**
     * This is just a demo. It shows an easy way to support multiple alternatives.
     * 
     * @param maze  the maze
     */
    public PathFinder( final Maze maze ){
        final int alternative = 1;
        switch( alternative ){
            case 1: // 1st alternative
                theActualPathFinder = new application.PathFinderDummy( maze );
                break;
            case 2: // 2nd alternative
                theActualPathFinder = new application.PathFinderDummy( maze );
                break;
            default: // default alternative
                theActualPathFinder = new application.PathFinderDummy( maze );
                break;
        }//switch
    }//constructor()
    
    
    
    @Override
    public List<Coordinate> getShortestPath() {
        return theActualPathFinder.getShortestPath();
    }//method()
    
}//class
