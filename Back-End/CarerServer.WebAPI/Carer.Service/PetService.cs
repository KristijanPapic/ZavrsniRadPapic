using Carer.Common;
using Carer.Model.DomainModels;
using Carer.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Service
{
    class PetService : IPetService
    {
        private IPetRepository petRepository;

        public PetService(IPetRepository petRepository)
        {
            this.petRepository = petRepository;
        }

        public async Task<List<PetDomainModel>> GetAllPetAsync(PetFilter filter)
        {
            return await petRepository.GetAllPetsAsync(filter);
        }

        public async Task PostPetAsync(PetDomainModel pet)
        {
           await petRepository.PostPetAsync(pet);
        }

        public async Task PutPetAsync(PetDomainModel pet)
        {
            await petRepository.PutPetAsync(pet);
        }

        public async Task DeletePetAsync(Guid id)
        {
           await petRepository.DeletePetAsync(id);
        }
    }
}
