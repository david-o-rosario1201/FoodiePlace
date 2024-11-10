package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.ReviewDao
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.ReviewRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReviewDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import java.time.LocalDate

class ReviewRepositoryTest {

    @Test
    fun addReseña() = runTest {
        // Given
        val Review = ReviewDTO(
            resenaId = 2,
            usuarioId = 2,
            comentario = "comentario 2",
            fechaResena = LocalDate.now().toString(),
            calificacion = 2
        )

        val reviewRemoteDataSource = mockk<ReviewRemoteDataSource>()
        val reviewDao = mockk<ReviewDao>()
        val repository = ReviewRepository(reviewRemoteDataSource, reviewDao)

        coEvery { reviewRemoteDataSource.postReseña(any()) } returns Review

        // When
        repository.addReseña(Review)

        // Then
        coVerify { reviewRemoteDataSource.postReseña(Review) }
    }


    @Test
    fun `Should add an usuario`() = runTest{

    }

    @Test
    fun getReseña() {
    }

    @Test
    fun deleteReseña() {
    }

    @Test
    fun updateReseña() {
    }

    @Test
    fun getReseñas() {
    }
}