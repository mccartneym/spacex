package uk.co.bits.spacex.domain.usecase

import uk.co.bits.spacex.domain.model.Launch
import uk.co.bits.spacex.domain.repository.LaunchesRepository
import javax.inject.Inject

class GetLaunchesUseCase @Inject constructor(private val launchesRepository: LaunchesRepository) {

    suspend operator fun invoke(): Result<List<Launch>> = launchesRepository.getLaunches()
}
