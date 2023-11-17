package com.gwj.recipesappV2.core.di

import com.gwj.recipesappV2.data.api.RecipeApi
import com.gwj.recipesappV2.data.repo.CategoryRepo
import com.gwj.recipesappV2.data.repo.CategoryRepoImpl
import com.gwj.recipesappV2.data.repo.GetAllMealsRepo
import com.gwj.recipesappV2.data.repo.GetAllMealsRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvider {

    @Provides
    @Singleton
    fun provideFoodsRepo(recipeApi: RecipeApi): CategoryRepo {
        return CategoryRepoImpl(recipeApi)
    }

    @Provides
    @Singleton
    fun provideGetAllMealsRepo(recipeApi: RecipeApi): GetAllMealsRepo {
        return GetAllMealsRepoImpl(recipeApi)
    }

}