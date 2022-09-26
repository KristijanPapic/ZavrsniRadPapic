using Carer.Common;
using Carer.Model.DomainModels;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Carer.Repository
{
    public interface IPetRepository
    {
        Task DeletePetAsync(Guid id);
        Task<List<PetDomainModel>> GetAllPetsAsync(PetFilter filter);
        Task PostPetAsync(PetDomainModel pet);
        Task PutPetAsync(PetDomainModel pet);
    }
}