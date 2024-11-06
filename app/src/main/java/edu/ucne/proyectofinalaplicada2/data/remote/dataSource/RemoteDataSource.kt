package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.FoodiePlaceApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodiePlaceApi: FoodiePlaceApi
) {

}