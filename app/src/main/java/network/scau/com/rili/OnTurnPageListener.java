package network.scau.com.rili;

/**
 * Created by Administrator on 2015/10/2 0002.
 */
public interface OnTurnPageListener {

    void OnLeftUp(int today,int month , int year );
    void OnLeftDown(int today,int month , int year );

    void OnRightUp(int today,int month , int year );
    void OnRightDown(int today,int month , int year );
}
