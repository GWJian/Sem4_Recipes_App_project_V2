package com.gwj.recipesappV2.core.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.gwj.recipesappV2.core.service.AuthService
import com.gwj.recipesappV2.core.service.StorageService
import com.gwj.recipesappV2.data.repo.favouriteRepo
import com.gwj.recipesappV2.data.repo.favouriteRepoRealTimeImpl
import com.gwj.recipesappV2.data.repo.UserRepo
import com.gwj.recipesappV2.data.repo.UserRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule{
    //========================================== AuthService Start =================================================
    @Provides
    @Singleton
    fun provideAuth():AuthService{
        return AuthService()
    }
//========================================== StoreService Start =================================================
    @Provides
    @Singleton
    fun provideStorageService(): StorageService {
        return StorageService()
    }
//========================================== StoreService End =================================================

//================================================= FIREBASE FireStore START ================================================
    @Provides
    @Singleton
    fun provideFirebasefavouriteCollection(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserRepoFireStore(db: FirebaseFirestore): UserRepo {
        return UserRepoImpl(db.collection("users"))
    }

//================================================= FIREBASE FireStore END ================================================


//================================================= FIREBASE Realtime START ================================================
    @Provides
    @Singleton
    fun provideFirebaseRealtimeRef(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("favourites")
    }

    @Provides
    @Singleton
    fun providefavouriteRepoRealtime(db:DatabaseReference):favouriteRepo{
        return favouriteRepoRealTimeImpl(db)
    }
//================================================= FIREBASE Realtime END ================================================



}