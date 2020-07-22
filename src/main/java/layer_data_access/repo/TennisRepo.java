package layer_data_access.repo;

import config.HibernateConfig;
import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class TennisRepo {

    private final static Session session = HibernateConfig.Instance();

    public static List<TennisMatch> findAllMatches(){
        Transaction transaction = session.beginTransaction();
        Query<TennisMatch> query = session.createQuery("from TennisMatch", TennisMatch.class);
        List<TennisMatch> list = query.getResultList();
        transaction.commit();
        return list;
    }

    public static List<TennisSet> findAllSets(){
        Transaction transaction = session.beginTransaction();
        Query<TennisSet> query = session.createQuery("from TennisSet", TennisSet.class);
        List<TennisSet> list = query.getResultList();
        transaction.commit();
        return list;
    }

    public static List<TennisGame> findAllGames(){
        Transaction transaction = session.beginTransaction();
        Query<TennisGame> query = session.createQuery("from TennisGame", TennisGame.class);
        List<TennisGame> list = query.getResultList();
        transaction.commit();
        return list;
    }

    public static TennisMatch findMatchById(int id){
        Transaction transaction = session.beginTransaction();
        Query<TennisMatch> query = session.createQuery("from TennisMatch t where t.id=:id");
        query.setParameter("id", id);
        TennisMatch tennisMatch = query.uniqueResult();
        transaction.commit();
        return tennisMatch;
    }

    public static TennisSet findSetById(int id){
        Transaction transaction = session.beginTransaction();
        Query<TennisSet> query = session.createQuery("from TennisSet t where t.id=:id");
        query.setParameter("id", id);
        TennisSet tennisSet = query.uniqueResult();
        transaction.commit();
        return  tennisSet;
    }

    public static List<TennisGame> findGamesFromSet(TennisSet tennisSet){
        Transaction transaction = session.beginTransaction();
        Query<TennisGame> query = session.createQuery("from TennisGame t where t.tennisSet=:tennisSet");
        query.setParameter("tennisSet", tennisSet);
        List<TennisGame> list = query.getResultList();
        transaction.commit();
        return list;
    }

    public static List<TennisSet> findSetsFromMatch(TennisMatch tennisMatch){
        Transaction transaction = session.beginTransaction();
        Query<TennisSet> query = session.createQuery("from TennisSet t where t.tennisMatch=:tennisMatch");
        query.setParameter("tennisMatch", tennisMatch);
        List<TennisSet> list = query.getResultList();
        transaction.commit();
        return list;
    }

    public static void deleteMatch(int id){
        Transaction transaction = session.beginTransaction();
        Query<TennisMatch> query = session.createQuery("delete from TennisMatch t where t.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
        transaction.commit();
    }

    public static void updateMatch(int id, User player1, User player2){
        Transaction transaction = session.beginTransaction();
        Query<TennisMatch> query = session.createQuery("update TennisMatch t set t.player1=:player1, t.player2=:player2 where t.id=:id");
        query.setParameter("id", id);
        query.setParameter("player1", player1);
        query.setParameter("player2", player2);
        query.executeUpdate();
        transaction.commit();
    }

    public static void updateGameScore(int id, String p1Score, String p2Score){
        Transaction transaction = session.beginTransaction();
        Query<TennisGame> query = session.createQuery("update TennisGame t set t.p1Score=:p1Score, t.p2Score=:p2Score where t.id=:id");
        query.setParameter("id", id);
        query.setParameter("p1Score", p1Score);
        query.setParameter("p2Score", p2Score);
        query.executeUpdate();
        transaction.commit();
    }
}